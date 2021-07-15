package com.assignment.repository

import com.assignment.models.constants.Constants

/**
 * @author Sajal Jain
 * @version 1.0
 * @since 13.07.2021
 */
object Client {
    val apis: Apis = AppRepositoryHelper(Constants.BASE_URL).apiInterface
}