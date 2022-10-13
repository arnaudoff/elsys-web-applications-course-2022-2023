JC=javac
JCFLAGS=-Xlint

JI=java
JIFLAGS=-classpath ./src

SRC=$(wildcard ./src/*/*.java)
CLS=$(SRC:.java=.class)

.PHONY: all
all: $(CLS) 

%.class: %.java
	$(JC) $(JCFLAGS) $<

run_server:
	$(JI) $(JIFLAGS) Server.Server

run_client:
	$(JI) $(JIFLAGS) Client.Client

.PHONY: clear
clear:
	rm -rf ./src/*/*.class
