## Slajdy

https://docs.google.com/presentation/d/1PFU6uBg9DHaw9EgBgsDkzeowFS5-YonYeIuL19gGx80/edit?usp=sharing

## Security

Identity provider well known configuration:

```
http://localhost:8080/auth/realms/sprio/.well-known/openid-configuration
```

Example authorization code initial redirect:

```
http://localhost:8080/auth/realms/sprio/protocol/openid-connect/auth?response_type=code&client_id=store-app&scope=openid&state=test&redirect_uri=http%3A%2F%2Flocalhost%3A9999%2Fitems
```

Example authorization code grant request:

```bash
curl -X POST \
  http://localhost:8080/auth/realms/sprio/protocol/openid-connect/token \
  -H 'content-type: application/x-www-form-urlencoded' \
  -d 'grant_type=authorization_code&client_id=store-app&client_secret=ca5bcf69-7f00-46d1-9ab1-dce91b86f00a&code=eyJhbGciOiJkaXIiLCJlbmMiOiJBMTI4Q0JDLUhTMjU2In0..P17EWm1-uO6mxMcQsq2hVg.K5czCwAHNeu1gBV_rreu9DmwQ2HvNiZ2hnO6UqsYKNRzWkdwMw98xrN7oXw20U9z1zVWBnTzhmDoliOH9mxlguaGbJUhXE5JvpO_I3B77TJN3gvNX6Z7wZ_W8AmHPIO4KdJk98vVIV9M15rjxDpQyczNI34QOHfujPou4MHCdkJPCArciQZRMYs93eA7aJxHuX5-2PXl63fZjKPLKS5PC5wVCgbEx-E4N2l5iDGhQzwLcbcmEUJP1FD4UinHMUCP.-Hppsz0VDyTqxbj3WXP7eA&redirect_uri=http%3A%2F%2Flocalhost%3A9999%2Fitems'
```

Example resource owner credentials grant request:

```bash
curl -X POST \
  http://localhost:8080/auth/realms/sprio/protocol/openid-connect/token \
  -H 'content-type: application/x-www-form-urlencoded' \
  -d 'grant_type=password&client_id=store-app&client_secret=ca5bcf69-7f00-46d1-9ab1-dce91b86f00a&username=james&password=sprio'
```
