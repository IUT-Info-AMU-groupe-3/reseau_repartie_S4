#include <arpa/inet.h>
#include <sys/socket.h>
#include <unistd.h>
#include <iostream>
#include <string.h>
#include <stdio.h>
#include <time.h>
#include <stdlib.h>
#define NUM_PORT 50013
#define BACKLOG 50
#define TAILLE_BUFFER 1024
using namespace std;

namespace{
    void exitErreur(const char * msg) {
        perror(msg);
        exit( EXIT_FAILURE);
    }
}

int main(int argc, char * argv[]) {
    int port;
    if (argc == 2)
        port = atoi(argv[1]);
    else
        port = NUM_PORT;

    int sock_serveur_udp = socket(AF_INET, SOCK_DGRAM, 0);
    int sock_serveur_tcp = socket(AF_INET, SOCK_STREAM, 0);
    struct sockaddr_in sockaddr_serveur;
    sockaddr_serveur.sin_family = AF_INET;
    sockaddr_serveur.sin_port = htons(port);
    sockaddr_serveur.sin_addr.s_addr = htonl(INADDR_ANY);
    int yes = 1;

    //UDP
    if (setsockopt(sock_serveur_udp, SOL_SOCKET, SO_REUSEADDR, &yes, sizeof(int))== -1)
        exitErreur("setsockopt");
    if (bind(sock_serveur_udp, (struct sockaddr *) &sockaddr_serveur,sizeof(sockaddr_in)) == -1)
        exitErreur("bind");

    //TCP
    if (setsockopt(sock_serveur_tcp, SOL_SOCKET, SO_REUSEADDR, &yes, sizeof(int))== -1)
        exitErreur("setsockopt");

    if (bind(sock_serveur_tcp, (struct sockaddr *) &sockaddr_serveur,
            sizeof(sockaddr_in)) == -1)
        exitErreur("bind");

    if (listen(sock_serveur_tcp, BACKLOG) == -1)
        exitErreur("listen");

    sockaddr_in sockaddr_client;
    socklen_t size = sizeof(sockaddr_client);
    char buf[TAILLE_BUFFER];
    char * msg;
    time_t date;
    fd_set rset;
    FD_ZERO(&rset);
    FD_SET(sock_serveur_udp, &rset);
    FD_SET(sock_serveur_tcp, &rset);
    int sock_client;
    cout << "Serveur lancé sur le port " << port << endl;

    for (;;) {
        select((sock_serveur_udp > sock_serveur_tcp) ? sock_serveur_udp + 1 : sock_serveur_tcp +1,&rset,NULL,NULL,NULL);
        date = time(NULL);
        msg = ctime(&date);
        if(FD_ISSET(sock_serveur_udp, &rset)){
            cout << "Serveur en mode UDP" << endl;
            recvfrom(sock_serveur_udp, buf, sizeof(buf), 0,(struct sockaddr *) &sockaddr_client, &size);
            cout << buf <<endl;
            if (sendto(sock_serveur_udp, msg, strlen(msg), 0,(struct sockaddr *) &sockaddr_client,sizeof(sockaddr_client))== -1)
                exitErreur("sendto");
        }else{
            cout << "Serveur en mode TCP" << endl;
            sock_client = accept(sock_serveur_tcp, NULL, NULL);
            if (sock_client == -1)
                exitErreur("accept");
            if (write(sock_client, msg, strlen(msg)) == -1)
                exitErreur("write");
        }

    }

    close(sock_serveur_udp);
    close(sock_serveur_tcp);
    return 0;
}
