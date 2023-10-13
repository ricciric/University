import requests

par = {'id' : 'flag'}
r = requests.get("http://web-02.challs.olicyber.it/server-records", params=par)
print(r.text)