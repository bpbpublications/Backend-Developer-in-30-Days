package main

import (
	"fmt"
	"log"
	"net"

	helloApi "example.com/grpc/api/models/helloworld"
	"golang.org/x/net/context"
	"google.golang.org/grpc"
)

type ServerHandler struct {
}

func (s *ServerHandler) SayHello(ctx context.Context, in *helloApi.Message) (*helloApi.Message, error) {
	log.Printf("Receive message body from client: %s", in.Body)
	return &helloApi.Message{Body: "Hello From the server!"}, nil
}

func main() {

	fmt.Println("Starting gRPC server!")

	listener, err := net.Listen("tcp", fmt.Sprintf(":%d", 9000))
	if err != nil {
		log.Fatalf("Error with server conenction: %v", err)
	}

	s := ServerHandler{}

	grpcServer := grpc.NewServer()

	helloApi.RegisterHelloServiceServer(grpcServer, &s)

	if err := grpcServer.Serve(listener); err != nil {
		log.Fatalf("Error serving response: %s", err)
	}
}