"""
$python3 test_rest.py

aka

$python3 -m test
"""

import requests

headers = {'Content-Type': 'application/json'}

response = requests.post("http://localhost:8080/api/users",
                         json={'username': 'Anna',
                               'primaryPhoneNumber': '123-456-7890',
                               'backupPhoneNumber': '111-111-1111'},
                         headers={'Content-Type': 'application/json'})

print(response.status_code)
print(response.json())
print(response.headers)

print(requests.get("http://localhost:8080/api/users",
                   headers={'Content-Type': 'application/json'}).json())

print(requests.post("http://localhost:8080/api/posts",
                    headers={'Content-Type': 'application/json'},
                    json={'postContent': 'words', 'postTitle': 'cuz maybe', 'userId': response.json()['userId']}).json())

print(requests.get("http://localhost:8080/api/users",
                   headers=headers).json())

print(requests.get("http://localhost:8080/api/posts", headers=headers).json()['posts'][0])
