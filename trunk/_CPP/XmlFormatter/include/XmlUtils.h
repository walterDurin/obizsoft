#include <string>


namespace StringTools
{
    // ....
    template <typename T>
    inline std::basic_string<T> Replace(const std::basic_string<T>& str, const std::basic_string<T>& find, const std::basic_string<T>& replace);
}

class XmlUtils
{
    public:

        static std::string formatXml(std::string s);
        static std::string formatXml(std::string s, int initialIndent);

        virtual ~XmlUtils();

/*
    private:
*/
    class XmlFormatter {
        public:
        XmlFormatter(int indentNumChars, int lineLength);
        std::string format(std::string s, int initialIndent);

        //private:
        int indentNumChars;
        int lineLength;
        bool singleLine;

        static std::string buildWhitespace(int numChars);

        /**
        * Wraps the supplied text to the specified line length.
        * @lineLength the maximum length of each line in the returned string (not including indent if specified).
        * @indent optional number of whitespace characters to prepend to each line before the text.
        * @linePrefix optional string to append to the indent (before the text).
        * @returns the supplied text wrapped so that no line exceeds the specified line length + indent, optionally with
        * indent and prefix applied to each line.
        */
        std::string lineWrap(std::string s, int lineLength, int indent, std::string linePrefix);

    };

    static XmlFormatter formatter;


};
