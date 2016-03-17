#include <arpa/inet.h>
#include <sys/socket.h>
#include <unistd.h>
#include <iostream>
#include <string.h>
#include <stdio.h>
#include <time.h>
#include <stdlib.h>
#define NUM_PORT 50013
#define TAILLE_BUFFER 1024
using namespace std;

namespace{
    void exitErreur(const char * msg) {
        perror(msg);
        exit( EXIT_FAILURE);
    }
}


int main(int argc, char *argv[])
{
    int port;
    char * server_addr;
    if (argc > 2){
        port = atoi(argv[2]);
        server_addr = argv[3];
    }
    else{
        port = NUM_PORT;
        server_addr = "139.124.187.79";
    }

    int sock_client = socket(AF_INET, SOCK_DGRAM, 0);
    struct sockaddr_in sockaddr_serveur;
    sockaddr_serveur.sin_family = AF_INET;
    sockaddr_serveur.sin_port = htons(port);
    inet_aton(server_addr, &sockaddr_serveur.sin_addr);
    socklen_t size = sizeof(sockaddr_serveur);

    char buf[TAILLE_BUFFER];
    char * msg = "hello";
    size_t n;
    cout << "client lancé " << endl;

    if (sendto(sock_client, msg, strlen(msg), 0,(struct sockaddr *) &sockaddr_serveur,sizeof(sockaddr_serveur))== -1)
        exitErreur("sendto");
    n = recvfrom(sock_client, buf, sizeof(buf), 0,(struct sockaddr *) &sockaddr_serveur, &size);
        cout << string(buf,n) << endl;

    close(sock_client);
    return 0;
}
