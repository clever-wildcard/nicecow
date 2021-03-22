import requests

body = {"email": "7686anna@gmail.com", "password": "anna"}
response = requests.post("http://localhost:8080/api/create-account", json=body)
print(response.url)
print(response.status_code)
print(response.headers)
print(response.content)