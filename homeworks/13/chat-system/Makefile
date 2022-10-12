all: Server Client

Server:
	javac ./src/$@/$@.java -Xlint

Client:
	javac ./src/$@/$@.java -Xlint

run_server:
	java -classpath ./src Server.Server

run_client:
	java -classpath ./src Client.Client

.PHONY:
clear:
	rm -rf ./src/*/*.class
