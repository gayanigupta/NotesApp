//getting date before 7 days
var d = new Date();
d.setDate(d.getDate() - 7);
d.setMonth(d.getMonth() + 1 - 0);
var str =  d.getFullYear() + "-" +  d.getMonth() + "-" + d.getDate();

//call to Github REST API
angular.module('issuesApp', [])
.controller('githubIssues', function($scope, $http) {
    $http.get('https://api.github.com/repos/angular/angular/issues?since=' + str).
        then(function(response) {
            $scope.issues = response.data;
        });
});