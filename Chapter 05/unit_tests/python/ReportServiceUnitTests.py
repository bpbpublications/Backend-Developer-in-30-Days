import unittest
from unittest.mock import patch
from ReportService import ReportService, PrintService

class FakePrintService:
    def __init__(self, company_name):
        self.company_name = company_name

    def create_print_job(self, job_to_print):
        print("This is a fake class")
        return self.company_name == job_to_print["company"]

class ReportServiceUnitTest(unittest.TestCase):
  company = "Company 1 Co."

  def setUp(self):
      print_service = FakePrintService(self.company)
      self.report_service = ReportService(self.company, print_service)

  def test_create_report(self):
      report = self.report_service.create_report()

      self.assertEqual(self.company, report["company"])

  @patch('ReportService.PrintService')
  def test_create_report_calls_print_service(self, mock_print_service):
      mock_print_service.create_print_job.return_value = True
      self.report_service = ReportService(self.company, mock_print_service)

      report = self.report_service.create_report()

      self.assertEqual(self.company, report["company"])
      self.assertEqual(mock_print_service.create_print_job.call_count, 1)

  @patch('ReportService.PrintService')
  def test_create_report_not_call_print_service(self, mock_print_service):
      self.report_service = ReportService(self.company, mock_print_service)

      report = self.report_service.create_report(should_print=False)

      self.assertEqual(self.company, report["company"])
      self.assertEqual(mock_print_service.create_print_job.call_count, 0)

  @patch('ReportService.PrintService')
  def test_create_report_throws_exception(self, mock_print_service):
      mock_print_service.create_print_job.return_value = False
      self.report_service = ReportService(self.company, mock_print_service)

      with self.assertRaises(Exception) as context:
        self.report_service.create_report()

      self.assertEqual('Could not print the report. Try again later', context.exception.args[0])

if __name__ == '__main__':
    unittest.main()