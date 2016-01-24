#include <stdio.h>
#include <fcntl.h>
#include <unistd.h>
#include <string.h>
#include <sys/stat.h>

int main(int argc, char** argv) {
  // create and write file
  const char* path = "/tmp/stat_test.log";
  mode_t mode = S_IRUSR | S_IWUSR | S_IRGRP | S_IROTH;
  int fd = open(path, O_WRONLY | O_CREAT | O_TRUNC, mode);
  char buf[] = "hello\n";
  int len = strlen(buf);
  if (write(fd, buf, len) != len) {
      printf("write error!");
  }
  close(fd);
  // stat
  struct stat state;
  if (stat(path, &state) != 0) {
    printf("stat occurs error!");
  }
  printf("st_uid=%d\n", state.st_uid);
  // fstat
  fd = open(path, O_RDONLY | O_APPEND, mode);
  if (fstat(fd, &state) != 0) {
    printf("fstat occurs error!");
  }
  printf("st_uid=%d\n", state.st_uid);
  // lstat
  // fstatat
}
