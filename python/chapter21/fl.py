import os
import json

def get_import_list(path):
    array = []
    for pdir in os.listdir(path):
        cdir = os.path.join(path, pdir)
        if os.path.isdir(cdir):
            for file in os.listdir(cdir):
                if file.startswith("table_list"):
                    array.append(os.path.join(pdir, file))
        else:
            if pdir.startswith("table_list"):
                array.append(pdir)
    return array

def get_export_list(path):
    array = []
    for file in os.listdir(path):
        if file.startswith("table_list") and file.find("vertical") == -1:
            array.append(file)
    return array

def get_file_list(base_dir, export_dir, import_dir):
    export_list = get_export_list(os.path.join(base_dir, export_dir))
    import_list = get_import_list(os.path.join(base_dir, import_dir))
    result = {"export_file_list": export_list, "import_file_list": import_list}
    return json.dumps(result)
    
def test():
    # test code should be write here
    pass
