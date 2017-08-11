# Products Web Service

This web service was created using the springboot framework and uses an embedded mongo database as a it storage layer.


## Running the project
1. first either clone or download the project from github
2. then run maven to install dependencies : `mvn clean install`
3. then use maven to start the springboot local program : `mvn spring-boot:run` **note : build may take up to a minute on first build due to dependency setup**
4. I used the open API spec to design and generate the base for this project so it includes a nice graphical interface for testing and visualing the service after the run process is complete open a web browser and navigate to :  `http://localhost:8080/example`
5. from there you can see example inputs and outputs and even test the service itself.
6. however, if you like to use something like cURL or postman the base url for the sevice is `http://localhost:8080/example/products`
7. the available methods are 
 * `POST /products` - creates new product.
 * `GET /products` - gets all products
 * `GET /products/{id}` - gets a single product.
 * `PATCH /products/{id}` - updates a single product.
 * `DELETE /products/{id}` - deletes a single product.

the input body for methods that create and update products are 
`{
	"name" : "string"
	"description" : "string"
}`

the resulting output body if successfully will look like this
`{
  "created": "datetime",
  "description": "string",
  "id": "string",
  "name": "string",
  "updated": "datetime"
}`


## Implementation Process

* The first thing I did when starting this project was to design the API out before writing a single line of code. The way i approached this and how I have done with previous projects is to design an API contract using open API specification.
* After I had written the API contract i went to editor.swagger.io and validated my API contract and used their code generation tool(which will generate project boilerplate for about 20 different languages and will auto generate client sdks for APIs) to generate a springboot server.
* From there I implemented the generic logic Data model validation, autowiring, creating interfaces for implementation classes, etc.
* I then decided to use Mongo as the datastore because I have implemented a good of mongo based rest servivces in javascript frameworks but always used relational datastores in java services and wanted to take on a new perspective.(if this were a real project, i would have decided on a datastore based off of read throughput, write throughput,data access patterns,scalability requirements,data consistency requirments,etc.)
* I designed the API uris in such a way that it would be simple enough for a developer/end user to consume it in a logical and familar way to other restful web servivces.
* If you have any questions related to this project or implmentation details please reach out to me at **williamstowersdev@gmail.com**