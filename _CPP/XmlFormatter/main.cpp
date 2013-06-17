#include <iostream>
#include <string>
#include <sstream>
#include "include/XmlUtils.h"
#include <windows.h>

using namespace std;

int main()
{

/*
    wcin >> noskipws;
    istream_iterator<wchar_t> in(wcin);
    istream_iterator<wchar_t> end;
    wstring results(in, end);
    wcout<<results;
*/


    wcin >> noskipws;
    wstringstream sb;
    wchar_t c ;
    while(true) {
        c = wcin.get();
        //wcin>>c;
        //wcout << c;
        if(wcin.eof()) {
            break;
        }
        sb << c;
    }

    wstring xml = sb.str();
    //wcout << xml.c_str();
    wcout << XmlUtils::formatXml(xml);

    return 0;
}

