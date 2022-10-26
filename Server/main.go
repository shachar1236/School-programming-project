package main

import (
	"net/http"

	"github.com/gin-gonic/gin"
	"gorm.io/driver/sqlite"
	"gorm.io/gorm"

	"fmt"
)

// making db
var db *gorm.DB

// My functions

const USERNAME_MIN_LEN = 5
const USERNAME_MAX_LEN = 20

const PASSWORD_MIN_LEN = 8
const PASSWORD_MAX_LEN = 20

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
    // adding user to database
    userDb := newUser.ConvertToUserModel()
    db.Create(&userDb)

    // sending veryfied
	c.String(http.StatusCreated, "Verifiyied")
    
}

// Server code
func login(c *gin.Context) {
}

func main() {
    rand_init()

    db, err := gorm.Open(sqlite.Open("server.db"), &gorm.Config{})
    if err != nil {
        panic("failed to connect database")
    }
    
    // Migrate the schema
    db.AutoMigrate(&UserModel{})

    router := gin.Default()
	router.POST("/signup", signup)
	router.POST("/login", login)
	router.Run("localhost:8080")
}
