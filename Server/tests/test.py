#!/usr/bin/python3
from Crypto.Signature import pkcs1_15
from Crypto.Hash import SHA256
from Crypto.PublicKey import RSA
import hashlib
import base64
import requests

private_key = RSA.generate(2048)
public_key = private_key.public_key()

data = {
    "username":"test123",
    "password":"X5h01$drlG!BjzWbQFi23PdsaDS#dsa12&@3",
    "email":"test@email.com",
    "user_public_key_pem" : public_key.export_key().decode()
}

before_hash = data["username"] + data["password"] + data["email"] + data["user_public_key_pem"]
print("After hash: ", [x for x in hashlib.sha256(before_hash.encode()).digest()])

h = SHA256.new(before_hash.encode())
signature = pkcs1_15.new(private_key).sign(h)
print(type(signature))

print("Signatue bytes: ", [x for x in signature])

data["signature"] = base64.b64encode(signature).decode()

print(data)

response = requests.post("http://127.0.0.1:8080/signup", json=data)

print(response.content)
