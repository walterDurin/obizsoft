#include <iostream>
#include <string>
#include <sstream>
#include "include/XmlUtils.h"
#include <windows.h>

using namespace std;

int main()
{

    std::stringstream sb;
    char c ;
    while(true) {
        c = cin.get();
        if(c<0) {
            break;
        }
        sb << c;
    }

    string xml = sb.str();
    cout << XmlUtils::formatXml(xml);
    return 0;
/*
    string xml = "<a><b>as<c>as</c></b><d/></a>";
    //string xml = "<a>bbb</a>";
    cout << "Xml: \n" << xml <<"\n";
    cout << XmlUtils::formatXml(xml);
    return 0;
*/
}

