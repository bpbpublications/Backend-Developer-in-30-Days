import logging
import os
import sys

logfile_name = os.path.dirname(sys.argv[0]) + "/logfile.log"
logentry_format = '%(levelname)s: %(asctime)s - %(name)s - %(message)s'

logging.basicConfig(format=logentry_format, level=logging.DEBUG)

logger = logging.getLogger('login.py')
handler = logging.FileHandler(logfile_name)
logger.addHandler(handler)

def openFileCatalog(file_name):
  return open(file_name, 'r')

def findCatalog(product_id):
  try:
    openFileCatalog("catalog.xml")
  except Exception as e:
    logger.error('Could not open the catalog for product {}'.format(product_id), exc_info=True)

findCatalog(123321)
# self.log = logging.getLogger(self.__class__.__name__)