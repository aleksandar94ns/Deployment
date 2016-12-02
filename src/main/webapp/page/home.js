app.controller('HomeController', function ($scope, $http, $state, $location, $log, $rootScope, $mdSidenav, $mdDialog, $interval, authenticationService) {

    $scope.page.current = 0;

    $scope.authService = authenticationService;

    $scope.refresh = function () {
        productsService.listRecommended(function (response) {
            $scope.products = response.data;
            $scope.products.forEach(function (product) {
                product.imageCounter = 0;
            });
        });
    };

    $scope.refresh();

    $scope.viewReviews = function (id) {
        $state.transitionTo('navigation.reviews', {productId: id});
    };

    $scope.postReview = function (product) {
        $mdDialog.show({
            parent: angular.element(document.body),
            templateUrl: 'dialog/createReview.html',
            controller: 'CreateReviewController',
            locals: {
                review: null,
                store: null,
                product: product
            }
        }).finally(function () {
            $scope.refresh();
        });
    };

    $scope.editReview = function (product) {
        var reviews = product.reviews.filter(function (review) {
           return $scope.authService.getUser().id === review.user.id;
        });
        $mdDialog.show({
            parent: angular.element(document.body),
            templateUrl: 'dialog/createReview.html',
            controller: 'CreateReviewController',
            locals: {
                review: reviews[0],
                store: null,
                product: product
            }
        }).finally(function () {
            $scope.refresh();
        });
    };

    $scope.addToShoppingCart = function (product) {
        shoppingCartsService.add(product, function () {
            $mdDialog.show(
                $mdDialog.alert()
                    .parent(angular.element(document.body))
                    .title(product.name + ' added to shopping cart!')
                    .content('You can checkout at any time or continue shopping!')
                    .ok('Ok')
            );
        });
    };

    $scope.switchImage = function (product) {
        product.imageCounter++;
    };
});
