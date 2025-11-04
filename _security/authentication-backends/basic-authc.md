---
layout: default
title: HTTP basic authentication
parent: Authentication backends
nav_order: 46
redirect_from:
---

# HTTP basic authentication

* HTTP basic authentication
  * username + password
  * configuration
    * `config.dynamic.authc.basic_internal_auth_domain.http_authenticator.type` == `basic`
    * `config.dynamic.authc.basic_internal_auth_domain.authentication_backend.type` == `internal`

* [internal user database](#internal-user-database)

Once `basic` is specified for the type of HTTP authenticator and `internal` is specified for the type of authentication backend, 
no further configuration in `config.yml` is needed, 
unless you plan to use additional authentication backends with HTTP basic authentication
Continue reading for considerations related to this type of setup and more information about the `challenge` setting.


## The challenge setting

In most cases, it's appropriate to set `challenge` to `true` for basic authentication
* This setting defines the behavior of the Security plugin when the `Authorization` field in the HTTP header is not specified
* By default, the setting is `true`. 

When `challenge` is set to `true`, the Security plugin sends a response with the status `UNAUTHORIZED` (401) back to the client
* If the client is accessing the cluster with a browser, this triggers the authentication dialog box and the user is prompted to enter a username and password
* This is a common configuration when HTTP basic authentication is the only backend being used.

When `challenge` is set to `false` and an `Authorization` header has not been specified in the request, the Security plugin does not send a `WWW-Authenticate` response back to the client, and authentication fails
* This configuration is often used when you have multiple challenging `http_authenticator` settings included in your configured authentication domains
* This might be the case, for example, when you plan to use basic authentication and SAML together
* For an example and a more complete explanation of this configuration, see [Running multiple authentication domains]({{site.url}}{{site.baseurl}}/security/authentication-backends/saml/#running-multiple-authentication-domains) in the SAML documentation.

When you define multiple HTTP authenticators, make sure to order non-challenging authenticators first---such as `proxy` and `clientcert`---and order challenging HTTP authenticators last
* For example, in a configuration where a non-challenging HTTP basic authentication backend is paired with a challenging SAML backend, you might specify `order: 0` in the HTTP basic `authc` domain and `order: 1` in the SAML domain.
{: .note }


## internal user database

* == [internal users](../configuration/yaml.md#internal_usersyml) + hashed passwords + other user attributes (roles,...)
  * kept | "internal_users.yml"
