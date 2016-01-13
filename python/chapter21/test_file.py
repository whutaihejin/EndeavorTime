
#
try:
    array = []
    file_handler = open("/home/taihejin/WorkTime/etl_scripts/wag_specs/sync_table_overwrite/sync_fengkai/table_list", "r")
    for line in file_handler.readlines():
        array.append(line)
    print array
finally:
    if file_handler:
        file_handler.close()

