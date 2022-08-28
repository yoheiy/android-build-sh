#include <stdio.h>
#include <ctype.h>
int
main(void)
{
   int c;
   int m = 0;

   while (c = getchar(), c != EOF) {
      if (c == '~') {
         int c2 = getchar();
         if (c2 == '"')
            m = 1;
         else
            putchar(toupper(c2)); }
      else if (m) {
         if (islower(c))
            putchar(toupper(c));
         else {
            m = 0;
            putchar(c); } }
      else
         putchar(c);
   }
   return 0;
}
