package main

import (
	"log"
	"net/http"

	"github.com/graphql-go/graphql"
	"github.com/graphql-go/handler"
)

type Membership struct {
	ID string `json:id`
	Name string `json:name`
	Orders []Order `json:orders`
}

type Order struct {
	ID    string   `json:"id"`
	Created_by  string  `json:"created_by"`
	Created_on  string  `json:"created_on"`
	Total float64 `json:"total"`
}


func main() {

	var orderType = graphql.NewObject(
		graphql.ObjectConfig{
			Name: "Order",
			Fields: graphql.Fields{
				"id": &graphql.Field{
					Type: graphql.Int,
				},
				"created_by": &graphql.Field{
					Type: graphql.String,
				},
				"created_on": &graphql.Field{
					Type: graphql.String,
				},
				"total": &graphql.Field{
					Type: graphql.Float,
				},
			},
		},
	)

	var membershipType = graphql.NewObject(
		graphql.ObjectConfig{
			Name: "Membership",
			Fields: graphql.Fields{
				"id": &graphql.Field{
					Type: graphql.Int,
				},
				"name": &graphql.Field{
					Type: graphql.String,
				},
				"orders": &graphql.Field{
					Type: graphql.NewList(orderType),
				},
				"total": &graphql.Field{
					Type: graphql.Float,
				},
			},
		},
	)
	// Schema
	fields := graphql.Fields{
		"membership": &graphql.Field{
			Type: membershipType,
			Resolve: func(p graphql.ResolveParams) (interface{}, error) {
				return Membership{
					ID: "23",
					Name: "membership1",
				}, nil
			},
		},
		"order": &graphql.Field{
			Type: orderType,
			Resolve: func(p graphql.ResolveParams) (interface{}, error) {
				return Order{
					ID: "123124",
					Created_by: "John Doe",
					Created_on: "2009-06-15T13:45:30",
					Total: 34.12,
				}, nil
			},
		},
	}
	rootQuery := graphql.ObjectConfig{Name: "RootQuery", Fields: fields}
	schemaConfig := graphql.SchemaConfig{Query: graphql.NewObject(rootQuery)}
	schema, err := graphql.NewSchema(schemaConfig)
	if err != nil {
		log.Fatalf("failed to create new schema, error: %v", err)
	}

	/*
	// Query
	query := `
		{
			hello
		}
	`
	params := graphql.Params{Schema: schema, RequestString: query}
	r := graphql.Do(params)
	if len(r.Errors) > 0 {
		log.Fatalf("failed to execute graphql operation, errors: %+v", r.Errors)
	}
	rJSON, _ := json.Marshal(r)
	fmt.Printf("%s \n", rJSON) // {"data":{"hello":"world"}}
	*/

	h := handler.New(&handler.Config{
		Schema: &schema,
		Pretty: true,
		GraphiQL: true,
	})

	http.Handle("/graphql", h)
	http.ListenAndServe(":8080", nil)
}