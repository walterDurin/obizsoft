#include <iostream>
#include <string>
#include <sstream>
#include "include/XmlUtils.h"
#include <windows.h>

using namespace std;

int main()
{

    /*
    const DWORD BUFFER_SIZE = 2000;
    char buffer[ BUFFER_SIZE ] ;

    HANDLE hPipeRead, hPipeWrite;
    CreatePipe( &hPipeRead, &hPipeWrite, 0, 0 );
    HANDLE hConOut = GetStdHandle( STD_OUTPUT_HANDLE );
    SetStdHandle( STD_OUTPUT_HANDLE, hPipeWrite );

    STARTUPINFO startInfo = { sizeof(STARTUPINFO) };
    PROCESS_INFORMATION procInfo;
    //CreateProcess( "c:/windows/system32/ipconfig.exe", "", 0, 0, false, 0, 0, 0, &startInfo, &procInfo );

    //SetStdHandle( STD_OUTPUT_HANDLE, hConOut );

    DWORD nRead = 0;
    ReadFile( hPipeRead, buffer, BUFFER_SIZE - 1, &nRead, 0 );
    buffer[ nRead ] = 0;

    cout << XmlUtils::formatXml(string(buffer));
    // std::cout << "\n=====\n" << buffer << "\n=====\n";
    */



/*
    string s;
    char ch;
    while(ch!='q') {
        cin.get(ch);
        //cin>>s;
        cout<<ch;
    }
*/

/*
    stringstream ss ;
    ss << "aaa";
    cout<<"|"<< ss.str() << "|" << XmlUtils::XmlFormatter::buildWhitespace(5)<<"|";
    string a = "asdasdaasddffg";
    cout << StringTools::Replace(a, string("a"), string("W")) <<"\n";
*/

    string xml("<a><b></b><c><d></d></c></a>");
    cout << "Xml: \n" << xml <<"\n";
    cout << XmlUtils::formatXml(xml);

    return 0;
}

