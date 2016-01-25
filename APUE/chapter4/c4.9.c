#include <stdio.h>
#include <sys/stat.h>

int main(int argc, char* argv[]) {
  struct stat statbuf;
  /* turn on set-group-ID and turn off group-execute */
  if (stat("foo", &statbuf) < 0) {
    printf("stat error for foo\n");
    return -1;
  }
  if (chmod("foo", (statbuf.st_mode & ~S_IXGRP) | S_ISGID) < 0) {
    printf("chmod error for foo\n");
    return -1;
  }

  /* set absolute mode to "rw-r--r--"*/
  if (chmod("bar", S_IRUSR | S_IWUSR | S_IRGRP | S_IROTH) < 0) {
    printf("chmod error for bar\n");
    return -1;
  }
  return 0;
}
