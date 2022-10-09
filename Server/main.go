package main

import (
	"net/http"

	"github.com/gin-gonic/gin"
	"gorm.io/gorm"

	// "gorm.io/driver/sqlite"
	"crypto"
	"crypto/rsa"
	"crypto/sha256"
    "crypto/x509"
    // "crypto/elliptic"
    "encoding/pem"
	"encoding/base64"
	"errors"
	"fmt"
	"math/big"
	"net/mail"

	"github.com/go-passwd/validator"
)

// GORM Models
type UserModel struct {
    gorm.Model
    Username string
    PasswordHash string
    PasswodSalt string
    Email string
    UserPublicKeyN big.Int
    UserPublicKeyE int
}

// Json Structs
type SignupJson struct {
    Username string `json:"username"`
    Password string `json:"password"`
    Email string `json:"email"`
    UserPublicKeyPem string `json:"user_public_key_pem"`
    SignatureBase64 string `json:"signature"` // signature of the json
}

// My functions

const USERNAME_MIN_LEN = 5
const USERNAME_MAX_LEN = 20

const PASSWORD_MIN_LEN = 8
const PASSWORD_MAX_LEN = 20

func (user *SignupJson) getPublicKey() *rsa.PublicKey {
	block, _ := pem.Decode([]byte(user.UserPublicKeyPem))
    if block == nil {
        fmt.Println("Invalid PEM Block")
        return nil
    }

    key, err := x509.ParsePKIXPublicKey(block.Bytes)
    if err != nil {
        fmt.Println(err)
        return nil
    }

    pubKey := key.(*rsa.PublicKey)

    return pubKey
}

func (user *SignupJson) getSignatureBytes() []byte {

    // if m := len(user.SignatureBase64) % 4; m != 0 {
    //     user.SignatureBase64 += strings.Repeat("=", 4-m)
    // }

    decodedSignature, err  := base64.StdEncoding.DecodeString(user.SignatureBase64)
    // decodedSignature, err  := base64.URLEncoding.WithPadding(base64.NoPadding).DecodeString(user.SignatureBase64)
    if err == nil {
        return decodedSignature
    }
    fmt.Println("Signature bytes error: ", err)
    return nil
}

func (user *SignupJson) getHash() []byte {
    h := sha256.New()
    beforeHash := user.Username + user.Password + user.Email + user.UserPublicKeyPem

    h.Write([]byte(beforeHash))
    hash_data := h.Sum(nil)
    fmt.Println("After hash: ", hash_data)
    return hash_data
}

func (user *SignupJson) verifySignature() bool {
    // publicKeyN := user.GetUserPublicKeyN()
    publicKey := user.getPublicKey()
    fmt.Println("Public key rsa: ", publicKey)
    hash_data := user.getHash()
    signature := user.getSignatureBytes()
    fmt.Println("Signature: ", user.SignatureBase64)
    fmt.Println("Signature bytes: ", signature)
    if signature == nil {
        return false
    }
    
    err := rsa.VerifyPKCS1v15(publicKey, crypto.SHA256, hash_data, signature)
    fmt.Println("Verify signature error: ", err)
    return err == nil
}

func (user *SignupJson) isGoodUserCredentials() (bool, string) {

    // check username
    usernameLen := len(user.Username)
    if usernameLen <  USERNAME_MIN_LEN || usernameLen > USERNAME_MAX_LEN {
        return false, "Username is not in the right length!"
    }

    // check password
    passwordValidator := validator.New(validator.MinLength(PASSWORD_MIN_LEN, errors.New("")), validator.MaxLength(PASSWORD_MAX_LEN, errors.New("")))
    if passwordValidator.Validate(user.Password) == nil {
        return false, "Weak password"
    }

    // check email
    _, err := mail.ParseAddress(user.Email)
    if err != nil {
        return false, "Email is not valid!"
    }

    // check signature
    if !user.verifySignature() {
        return false, "Wrong signature"
    }
    
    return true, ""
}

// Server code
func signup(c *gin.Context) {
    var newUser SignupJson

	if err := c.BindJSON(&newUser); err != nil {
        c.JSON(http.StatusBadRequest, gin.H{
          // "error":  "Json is not valid",
          "error":  err.Error(),
        })
		return
	}

    // check if valid message
    valid, err := newUser.isGoodUserCredentials()
    if !valid {
        c.JSON(http.StatusBadRequest, gin.H{
          "error":  err,
        })
        fmt.Println("Not valid")
		return
    }
	// c.IndentedJSON(http.StatusCreated, newBook)
	c.String(http.StatusCreated, "Verifiyied")
    
}

func main() {
    router := gin.Default()
	router.POST("/signup", signup)
	router.Run("localhost:8080")
}
