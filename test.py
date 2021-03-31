"""
$python3 test.py

aka

$python3 -m test
"""

import requests

response = requests.post("http://localhost:8080/user/save",
                         json={'name': 'Anna',
                               'surname': 'get out',
                               'email': 'leave me alone',
                               'username': 'username',
                               'password': 'no'},
                         headers={'Content-Type': 'application/json'})

print(response.status_code)
print(response.json())
print(response.headers)

print(requests.get("http://localhost:8080/user/all",
                   headers={'Content-Type': 'application/json'}).json())
