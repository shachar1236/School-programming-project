package main

import (
	"math/big"

	"gorm.io/gorm"
)

const PASSWORD_HASH_LENGTH = 32
const PASSWORD_HASH_SALT_LENGTH = 32

type UserModel struct {
	gorm.Model
	Username       string
	PasswordHash   [PASSWORD_HASH_LENGTH]byte
	PasswodSalt    [PASSWORD_HASH_SALT_LENGTH]byte
	Email          string
	UserPublicKeyN big.Int
	UserPublicKeyE int
}

