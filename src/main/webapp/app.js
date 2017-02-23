/**
 * Created by Alex on 10/29/16.
 */
'use strict';

var app = angular.module('app', ['ui.router', 'ngMessages', 'ngMaterial']);

app.factory('authInterceptor', ['$q', '$injector', function ($q, $injector) {
    var authInterceptor = {
        responseError: function (response) {
            var $state = $injector.get('$state');
            if ($state.current.name !== 'login' && response.status == 401) {
                $state.transitionTo('login');
            }
            return $q.reject(response);
        }
    };
    return authInterceptor;
}]);

app.config(function ($stateProvider, $locationProvider, $urlRouterProvider, $httpProvider, $mdThemingProvider) {

    $mdThemingProvider.theme('default')
        .accentPalette('orange');

    $urlRouterProvider.otherwise('/login');

    $httpProvider.interceptors.push('authInterceptor');

    $stateProvider
        .state('login', {
            url: '/login',
            controller: 'LoginController',
            templateUrl: 'page/login.html'
        })
        .state('navigation', {
            url: '',
            abstract: true,
            controller: 'NavigationController',
            templateUrl: 'page/navigation.html'
        })
        .state('navigation.home', {
            url: '/home',
            controller: 'HomeController',
            templateUrl: 'page/home.html'
        })
        .state('navigation.restaurantManagers', {
            url: '/restaurantManagers',
            controller: 'RestaurantManagersController',
            templateUrl: 'page/restaurantManagers.html'
        })
        .state('navigation.systemManagers', {
            url: '/systemManagers',
            controller: 'SystemManagersController',
            templateUrl: 'page/systemManagers.html'
        })
        .state('navigation.restaurants', {
            url: '/restaurants',
            controller: 'RestaurantsController',
            templateUrl: 'page/restaurants.html'
        })
        .state('navigation.waiters', {
            url: '/waiters',
            controller: 'WaitersController',
            templateUrl: 'page/waiters.html'
        })
        .state('navigation.bartenders', {
            url: '/bartenders',
            controller: 'BartendersController',
            templateUrl: 'page/bartenders.html'
        })
        .state('navigation.sellers', {
            url: '/sellers',
            controller: 'SellersController',
            templateUrl: 'page/sellers.html'
        })
        .state('navigation.drinkCards', {
            url: '/drinkCards',
            controller: 'DrinkCardsController',
            templateUrl: 'page/drinkCards.html'
        })
        .state('navigation.drinkItems', {
            url: '/drinkItems',
            controller: 'DrinkItemsController',
            templateUrl: 'page/drinkItems.html'
        })
        .state('navigation.chefs', {
            url: '/chefs',
            controller: 'ChefsController',
            templateUrl: 'page/chefs.html'
        })
        .state('navigation.profile', {
            url: '/profile',
            controller: 'ProfileController',
            templateUrl: 'page/profile.html'
        });
});