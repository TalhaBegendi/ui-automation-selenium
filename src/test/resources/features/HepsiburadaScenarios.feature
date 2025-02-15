Feature: HepsiburadaCases

  Background:
    Given Click "common.cookieBannerApplyButton" elements
    Then Check page url contains "https://www.hepsiburada.com"
    And Wait for given seconds 1
    And Click "search.clickSearchbar" elementss
    And Wait for given seconds 1

  Scenario: Scenario 1
    And Fill "search.product" field with "iphone"
    And Wait for given seconds 2

  Scenario: Scenario 2
    And Fill "search.product" field with "iphone"
    And Wait for given seconds 2

  Scenario: Scenario 3
    And Fill "search.product" field with "iphone"
    And Wait for given seconds 2