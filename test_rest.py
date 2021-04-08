"""
$python3 test_rest.py

aka

$python3 -m test
"""

import requests
headers={'Content-Type': 'application/json'}

response = requests.post("http://localhost:8080/create-account",
                         json={'name': 'Anna',
                               'surname': 'get out',
                               'email': 'leave me alone',
                               'username': 'username',
                               'password': 'no'},
                         headers={'Content-Type': 'application/json'})

print(response.status_code)
print(response.json())
print(response.headers)

print(requests.get("http://localhost:8080/users",
                   headers={'Content-Type': 'application/json'}).json())

print(requests.post("http://localhost:8080/create-post",
                              headers={'Content-Type': 'application/json'},
                              json={'postContent': 'words', 'postTitle': 'cuz maybe', 'userId': 1}).json())

print(requests.get("http://localhost:8080/users",
                   headers=headers).json())

print(requests.get("http://localhost:8080/posts", headers=headers).json())