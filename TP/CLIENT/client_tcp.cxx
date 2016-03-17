#include <arpa/inet.h>
#include <iostream>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#define NUM_PORT 50013
#define TAILLE_BUFFER 1024

using namespace std;

void exitErreur(const char * msg) {
    perror(msg);
    exit( EXIT_FAILURE);

}

int main(int argc, char * argv[]){

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


int sock_client = socket(AF_INET, SOCK_STREAM,0);

struct sockaddr_in sockaddr_serveur;

sockaddr_serveur.sin_family = AF_INET;

inet_aton(server_addr , &sockaddr_serveur.sin_addr);

sockaddr_serveur.sin_port = htons(port) ;


struct sockaddr_in sockaddr_client ;
socklen_t addrlen = sizeof(struct sockaddr_in) ;

if (connect(sock_client, (struct sockaddr *)&sockaddr_serveur, sizeof (sockaddr_serveur)) == -1)
    exitErreur("connect");

//getsockname(sock_client, (struct sockaddr *) & sockaddr_client, & addrlen ) ;
//cout << "L'adresse de la socket du client est " << inet_ntoa(sockaddr_client.sin_addr) <<":"<< sockaddr_client.sin_port<< endl ;

int n ;

char msg[TAILLE_BUFFER] ;

while ( 1 ) {

    n = read(sock_client, msg, TAILLE_BUFFER);

    // gestion des erreurs : par exemple un recv avec un char * sans allocation
    if ( n == -1  )
        exitErreur("read");

    // recv() retourne 0 quand la socket est fermée
    if( !n) break ;

    std::cout << string (msg, n) ;

}

close(sock_client);

}
