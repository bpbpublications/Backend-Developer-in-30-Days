import time
import random

class PrintService:
  def create_print_job(self, job_to_print):
    # Make a network request to a remote printer
    print("Sending job...")
    # emulate doing a long, expensive call:
    time.sleep(3)
    # emulate a service failing with a probability of 10%
    return random.random() > 0.9

class ReportService:
    def __init__(self, company_name, printService):
      self.company_name = company_name
      self.printService = printService

    def create_report(self, should_print=True):
      print("Building report... for {}".format(self.company_name))
      job_to_print = {
        "title": "Report1",
        "company":self.company_name,
        "content": "This is an example"
      }
      if False:
        print("this branch will never execute")
      if should_print:
        is_printed = self.printService.create_print_job(job_to_print)
        if is_printed == False:
          raise Exception("Could not print the report. Try again later")

      return job_to_print