---
layout: default
title: Configuration
nav_order: 2
has_children: true
has_toc: true
redirect_from:
  - /security-plugin/configuration/
  - /security-plugin/configuration/index/
  - /security/configuration/
---

# Security configuration

* Security plugin
  * includes
    * 👀demo certificates👀
      * Reason:🧠QUICKLY get up & run🧠
  * recommendations
    * | production,
      * change MANUALLY
        * demo certificates
        * other configuration options 

## Replace the demo certificates

* uses
  * | production

* steps
  1. [Generate your own certificates](generate-certificates)
  2. [Store the generated certificates & private key | appropriate directory](generate-certificates.md#add-certificate-files-to-opensearchyml) 
     * typically | "<OPENSEARCH_HOME>/config/"
  3. **Set the following file permissions:**
     * Private key (.key files)
       * Set file mode == `600`
         * == ONLY the file owner (== OpenSearch user) can read & write to the file
           * Reason:🧠private key remains secure & inaccessible | unauthorized users🧠
     * Public certificates (.crt, .pem files)
       * Set file mode == `644`
         * file owner can read & write to the file
         * OTHER users can ONLY read it.
        
        | Item        | Sample              | Numeric | Bitwise      |
        |-------------|---------------------|---------|--------------|
        | Public key  | `~/.ssh/id_rsa.pub` | `644`   | `-rw-r--r--` |
        | Private key | `~/.ssh/id_rsa`     | `600`   | `-rw-------` |
        | SSH folder  | `~/.ssh`            | `700`   | `drwx------` |

* [how to configure basic security settings](../../_install-and-configure/install-opensearch/docker.md#configuring-basic-security-settings)

## Reconfigure `opensearch.yml` to use your certificates

The `opensearch.yml` file is the main configuration file for OpenSearch; you can find the file at `<OPENSEARCH_HOME>/config/opensearch.yml`
* Use the following steps to update this file to point to your custom certificates:

In `opensearch.yml`, set the correct paths for your certificates and keys, as shown in the following example:
   ```
   plugins.security.ssl.transport.pemcert_filepath: /path/to/your/cert.pem
   plugins.security.ssl.transport.pemkey_filepath: /path/to/your/key.pem
   plugins.security.ssl.transport.pemtrustedcas_filepath: /path/to/your/ca.pem
   plugins.security.ssl.http.enabled: true
   plugins.security.ssl.http.pemcert_filepath: /path/to/your/cert.pem
   plugins.security.ssl.http.pemkey_filepath: /path/to/your/key.pem
   plugins.security.ssl.http.pemtrustedcas_filepath: /path/to/your/ca.pem
   ```
For more information, see [Configuring TLS certificates]({{site.url}}{{site.baseurl}}/security/configuration/tls/).

## Reconfigure `config.yml` to use your authentication backend

The `config.yml` file allows you to configure the authentication and authorization mechanisms for OpenSearch
* Update the authentication backend settings in `<OPENSEARCH_HOME>/config/opensearch-security/config.yml` according to your requirements. 

For example, to use the internal authentication backend, add the following settings:

  ```
    authc:
      basic_internal_auth:
        http_enabled: true
        transport_enabled: true
        order: 1
        http_authenticator:
          type: basic
          challenge: true
        authentication_backend:
          type: internal
   ```
For more information, see [Configuring the Security backend]({{site.url}}{{site.baseurl}}/security/configuration/configuration/).

## Modify the configuration YAML files

Determine whether any additional YAML files need modification, for example, the `roles.yml`, `roles_mapping.yml`, or `internal_users.yml` files. Update the files with any additional configuration information. For more information, see [Modifying the YAML files]({{site.url}}{{site.baseurl}}/security/configuration/yaml/).

## Set a password policy

When using the internal user database, we recommend enforcing a password policy to ensure that strong passwords are used. For information about strong password policies, see [Password settings]({{site.url}}{{site.baseurl}}/security/configuration/yaml/#password-settings).

## Apply changes using the `securityadmin` script

The following steps do not apply to first-time users because the security index is automatically initialized from the YAML configuration files when OpenSearch starts.
{: .note}

After initial setup, if you make changes to your security configuration or disable automatic initialization by setting `plugins.security.allow_default_init_securityindex` to `false` (which prevents security index initialization from `yaml` files), you need to manually apply changes using the `securityadmin` script:

1. Find the `securityadmin` script. The script is typically stored in the OpenSearch plugins directory, `plugins/opensearch-security/tools/securityadmin.[sh|bat]`. 
   - Note: If you're using OpenSearch 1.x, the `securityadmin` script is located in the `plugins/opendistro_security/tools/` directory. 
   - For more information, see [Basic usage]({{site.url}}{{site.baseurl}}/security/configuration/security-admin/#basic-usage).
2. Run the script by using the following command:
   ```
    ./plugins/opensearch-security/tools/securityadmin.[sh|bat]
   ```
3. Check the OpenSearch logs and configuration to ensure that the changes have been successfully applied.

For more information about using the `securityadmin` script, see [Applying changes to configuration files]({{site.url}}{{site.baseurl}}/security/configuration/security-admin/).

## Add users, roles, role mappings, and tenants

If you don't want to use the Security plugin, you can disable it by adding the following setting to the `opensearch.yml` file:

```
plugins.security.disabled: true
```

You can then enable the plugin by removing the `plugins.security.disabled` setting.

For more information about disabling the Security plugin, see [Disable security]({{site.url}}{{site.baseurl}}/security/configuration/disable-enable-security/).

The Security plugin has several default users, roles, action groups, permissions, and settings for OpenSearch Dashboards that contain "Kibana" in their names. We will change these names in a future version.
{: .note }

For a full list of `opensearch.yml` Security plugin settings, see [Security settings]({{site.url}}{{site.baseurl}}/install-and-configure/configuring-opensearch/security-settings/).
{: .note}

