app.controller('StatisticsController', function ($scope, $http, $state, $location, $log, $rootScope, $mdDialog, gradesService, reservationsService, guestReservationsService) {

    $scope.page.current = 16;

    $scope.grades = [];
    $scope.reservations = [];
    $scope.guestReservations = [];
    $scope.totalGuests = 0;
    $scope.restaurant = null;
    $scope.resGrade = 0;
    $scope.resGrades = 0;
    $scope.weiGrade = 0;
    $scope.weiGrades = 0;
    $scope.mealGrade = 0;
    $scope.mealGrades = 0;

    var loadGrades = function () {
        $scope.grades = [];
        $scope.resGrade = 0;
        $scope.resGrades = 0;
        $scope.weiGrade = 0;
        $scope.weiGrades = 0;
        $scope.mealGrade = 0;
        $scope.mealGrades = 0;
        $scope.guestReservations = [];
        $scope.totalGuests = 0;
        gradesService.list(function (response) {
            $scope.grades = response.data;
            $scope.grades.forEach(function (grade) {
                $scope.resGrades = $scope.resGrades + grade.restaurant;
                $scope.weiGrades = $scope.weiGrades + grade.waiter;
                $scope.mealGrades = $scope.mealGrades + grade.meal;
                guestReservationsService.create(grade.reservation, function (response) {
                    $scope.guestReservations = response.data;
                    $scope.guestReservations.forEach(function () {
                        $scope.totalGuests++;
                    });
                })
            });
            $scope.resGrade = $scope.resGrades/$scope.grades.length;
            $scope.weiGrade = $scope.weiGrades/$scope.grades.length;
            $scope.mealGrade = $scope.mealGrades/$scope.grades.length;
        });
    };

    var loadReservations = function () {
        $scope.restaurant = null;
        reservationsService.listMen(function (response) {
            $scope.reservations = response.data;
            $scope.reservations.forEach(function (res) {
                $scope.restaurant = res.restaurant;
            });
        });
    };

    loadReservations();

    loadGrades();
});