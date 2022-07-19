import unittest
from Calculator import Calculator

class CalculatorUnitTest(unittest.TestCase):

  def setUp(self):
      self.calculator = Calculator()

  def test_addition(self):
      self.assertEqual(2, self.calculator.add(1,1))

if __name__ == '__main__':
    unittest.main()