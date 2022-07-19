import unittest
from selenium import webdriver

class IntegrationTest(unittest.TestCase):
  def setUp(self):
      self.driver = webdriver.Chrome('./chromedriver')

  def test_integration(self):
    driver = self.driver
    driver.get("http://localhost:8000")

    self.assertEqual("Report Generator", driver.title)
    elem = driver.find_element_by_id("company-search-field")
    button = driver.find_element_by_id("search-button")
    elem.clear()
    elem.send_keys("Company 1")
    button.click()
    self.assertIn("Found 2 results", driver.page_source)

  def tearDown(self):
      self.driver.quit()
