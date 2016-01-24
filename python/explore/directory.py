import os


def filter(fileName):
    name = os.path.basename(fileName)
    # condition to filter
    flag = name.startswith("table_list") and name.find("vertical") == -1
    return True if flag else False


def walk(rootDir, resultArray, filter):
    for lists in os.listdir(rootDir):
        path = os.path.join(rootDir, lists)
        # print path
        if os.path.isdir(path):
            walk(path, resultArray, filter)
        elif filter(path):
            resultArray.append(path)
        else:
            # ignored
            pass


resultArray = []
walk(' ', resultArray, filter)
for item in resultArray:
    print item

# def usage():
#     print '-h display this help message.'
#     print '-p the root path to walk.'
#     print '-q to quit the program.'
#
#
# while True:
#     usage()
#     cmd = raw_input('>')
#     if (cmd == '-h' or cmd == 'h'):
#         usage()
#     elif (cmd == '-q' or cmd == 'q'):
#         break;
#     elif (cmd.startswith('-p')):
#         root = cmd.replace('-p ', '')
#         walk(root)



