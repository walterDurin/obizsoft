#include <string>
#include "../include/XmlUtils.h"
#include <sstream>
#include <algorithm>


namespace StringTools
{
    //
    template <typename T>
    inline std::basic_string<T> Replace(const std::basic_string<T>& str, const std::basic_string<T>& find, const std::basic_string<T>& replace)
    {
        std::basic_string<T> out = str;
        for (std::size_t i = 0; (i = out.find(find, i)) != std::wstring::npos; i += replace.size())
        {
            out.replace(i, find.size(), replace);
        }
        return out;
    }
    // ....
}

XmlUtils::XmlFormatter::XmlFormatter(int indentNumChars, int lineLength){
    this->indentNumChars = indentNumChars;
    this->lineLength = lineLength;
}

std::string XmlUtils::XmlFormatter::format(std::string s, int initialIndent) {
    int indent = initialIndent;
    std::stringstream sb;
    for (int i = 0; i < s.length(); i++)
    {
        char currentChar = s.at(i);
        if (currentChar == '<')
        {
            char nextChar = s.at(i + 1);
            if (nextChar == '/')
                indent -= indentNumChars;
            if (!singleLine)   // Don't indent before closing element if we're creating opening and closing elements on a single line.
                sb<<buildWhitespace(indent);
            if (nextChar != '?' && nextChar != '!' && nextChar != '/')
                indent += indentNumChars;
            singleLine = false;  // Reset flag.
        }
        sb << currentChar;
        if (currentChar == '>')
        {
            if (s.at(i - 1) == '/')
            {
                indent -= indentNumChars;
                sb<<"\n";
            }
            else
            {
                int nextStartElementPos = s.find_first_of('<', i);
                if (nextStartElementPos > i + 1)
                {
                std::string textBetweenElements = s.substr(i + 1, nextStartElementPos - i + 1);

                // If the space between elements is solely newlines, let them through to preserve additional newlines in source document.

                if (StringTools::Replace(textBetweenElements, std::string("\n"), std::string("")).length() == 0)
                {
                    sb << textBetweenElements << "\n";
                }
                // Put tags and text on a single line if the text is short.
                else if (textBetweenElements.length() <= lineLength * 0.5)
                {
                    sb << textBetweenElements;
                    singleLine = true;
                }
                // For larger amounts of text, wrap lines to a maximum line length.
                else
                {
                    sb << "\n" << lineWrap(textBetweenElements, lineLength, indent, NULL) << "\n";
                }
                i = nextStartElementPos - 1;
                }
                else
                {
                sb << "\n";
                }
            }
        }
    }
    return sb.str();
}

std::string XmlUtils::XmlFormatter::buildWhitespace(int numChars){
    std::stringstream ss;
    for (int i = 0; i < numChars; i++)
        ss<<" ";
    return ss.str();
}

std::string XmlUtils::XmlFormatter::lineWrap(std::string s, int lineLength, int indent, std::string linePrefix)
{

    if (s.empty())
        return s;

    std::stringstream sb;
    int lineStartPos = 0;
    int lineEndPos;
    bool firstLine = true;
    while(lineStartPos < s.length())
    {
        if (!firstLine)
            sb << "\n";
        else
            firstLine = false;

        if (lineStartPos + lineLength > s.length())
            lineEndPos = s.length() - 1;
        else
        {
            lineEndPos = lineStartPos + lineLength - 1;
            while (lineEndPos > lineStartPos && (s.at(lineEndPos) != ' ' && s.at(lineEndPos) != '\t'))
            lineEndPos--;
        }
        sb << buildWhitespace(indent);
        if (linePrefix.empty())
            sb << linePrefix;

        sb << s.substr(lineStartPos, lineEndPos - lineStartPos);
        lineStartPos = lineEndPos + 1;
    }
    return sb.str();
}

XmlUtils::XmlFormatter XmlUtils::formatter(4, 120);

std::string XmlUtils::formatXml(std::string s) {
    return formatter.format(s, 0);
}

std::string XmlUtils::formatXml(std::string s, int initialIndent){
    return formatter.format(s, initialIndent);
}

XmlUtils::~XmlUtils()
{
    //dtor
}
