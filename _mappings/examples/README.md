# how has it been created?
* `docker compose up -d`
* | browser,
  * http://localhost:5601/
    * admin/admin
    * choose whatever tenant

# Types
## Dynamic mapping
* TODO:
## Explicit mapping
### define the EXACT structure & data types
* | browser,
  * http://localhost:5601/
  * left panel > Management > Dev Tools >

    ```json
    PUT sample-index1
    {
      "mappings": {
        "properties": {
          "year":    { "type" : "text" },
          "age":     { "type" : "integer" },
          "director":{ "type" : "text" }
        }
      }
    }
    ```
#### EXISTING field's mapping can NOT be changed
* TODO:

#### EXISTING field's mapping parameters can be modified
* TODO:

