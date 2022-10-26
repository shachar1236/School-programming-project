package main

import (
	"time"
)

const SESSION_ID_SIZE = 128

type UserSession struct {
	username   string
	user_id    int
	session_id string
	creation_time int64
}

func GenerateUserSession(username string, user_id int) UserSession {
	user := UserSession{
		username: username,
		user_id: user_id,
		session_id: RandStringRunes(SESSION_ID_SIZE),
		creation_time: time.Now().Unix(),
	}
	return user
}
