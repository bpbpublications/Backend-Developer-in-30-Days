package main

import (
	"log"

	"golang.org/x/net/context"
	"google.golang.org/grpc"

	helloApi "example.com/grpc/api/models/helloworld"
)

func main() {

	var conn *grpc.ClientConn
	conn, err := grpc.Dial(":9000", grpc.WithInsecure())
	if err != nil {
		log.Fatalf("did not connect: %s", err)
	}
	defer conn.Close()

	c := helloApi.NewHelloServiceClient(conn)

	response, err := c.SayHello(context.Background(), &helloApi.Message{Body: "Hello From client!"})
	if err != nil {
		log.Fatalf("Error when calling SayHello: %s", err)
	}
	log.Printf("Response from server: %s", response.Body)

}