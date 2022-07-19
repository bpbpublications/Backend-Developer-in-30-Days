build:
	rm client
	rm main
	protoc --go_out=plugins=grpc:api proto/helloworld.proto
	go build ./server.go
	go build ./client.go


buildproto:
	protoc --go_out=plugins=grpc:api proto/helloworld.proto

buildserver:
	go build ./server.go

buildclient:
	go build ./client.go