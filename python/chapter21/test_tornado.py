import tornado.ioloop
import tornado.web

import fl

class MainHandler(tornado.web.RequestHandler):
    def get(self):
        self.write("Hello world")

class StoryHandler(tornado.web.RequestHandler):
    def get(self, story_id):
        items = ["taihejin", "weibo"]
        items.append(story_id)
        self.render("index.html", items=items)
        # self.write("You requested the story " + story_id)

class FilelistHandler(tornado.web.RequestHandler):
    def get(self):
        base_dir = "/home/taihejin/WorkTime/etl_scripts/wag_specs"
        export_dir = "sync_table2"
        import_dir = "sync_table_overwrite"
        self.write(fl.get_file_list(base_dir, export_dir, import_dir))

def make_app():
  return tornado.web.Application([
    (r"/", MainHandler),
    (r"/story/([0-9]+)", StoryHandler),
    (r"/autosync/filelist", FilelistHandler)
    ])

if __name__ == "__main__":
  application = make_app()  
  application.listen(8888)
  tornado.ioloop.IOLoop.instance().start()
