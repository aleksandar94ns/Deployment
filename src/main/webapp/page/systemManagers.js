app.controller('SystemManagersController', function ($scope, $http, $state, $location, $log, $rootScope, $mdDialog, $interval, systemManagersService) {

    $scope.page.current = -1;

    systemManagersService.list(function (response) {
        $scope.systemManagers = response.data;
    });

    $scope.addSystemManager = function() {
        $mdDialog.show({
            parent: angular.element(document.body),
            templateUrl: 'dialog/createSystemManager.html',
            controller: 'CreateSystemManagerController'
        }).finally(function () {
            systemManagersService.list(function (response) {
                $scope.systemManagers = response.data;
            });
        });
    };

});

