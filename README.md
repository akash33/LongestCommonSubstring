# LongestCommonSubstring
Finds longest common substrings from given list

### Build Project
`mvn clean package`

### Run Project
`java -jar target\lcs-0.0.1-SNAPSHOT.jar`

### Curl Request

```
curl --location --request POST 'http://localhost:8080/lcs' \
   --header 'Content-Type: application/json' \
   --data-raw '{
       "setOfStrings": [
               {
                 "value": "comcast"
               },
               {
                 "value": "cast"
               }
           ]
   }
 ```
