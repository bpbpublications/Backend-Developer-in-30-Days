import logging
import os
import sys
from time import sleep
import random

logfile_name = os.path.dirname(sys.argv[0]) + "/logfile.log"
logentry_format = '%(levelname)s: %(asctime)s - %(message)s'

logger = logging.getLogger('logstash-test.py')
formatter = logging.Formatter(logentry_format)

handler = logging.FileHandler(logfile_name)
handler.setFormatter(formatter)
logger.addHandler(handler)
logger.setLevel(logging.DEBUG)


for i in range(60):
    for i in range(random.randint(0, 200)):
        logger.warning("This is warn")

    for i in range(random.randint(0, 200)):
        try:
            raise FileNotFoundError("config.xml")
        except Exception as e:
            logger.error('Could not open the config file', exc_info=True)


    for i in range(random.randint(0, 200)):
        try:
            raise Exception("Product does not exist")
        except Exception as e:
            logger.error('Could not create product', exc_info=True)
    sleep(2)