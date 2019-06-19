define(['../module'], function (filters) {
    'use strict';

    return filters.filter('getRol', function() {
            return function(input, idrol) {
              var i=0, len=input.length;
              for (; i<len; i++) {
                if (input[i].idrol == idrol) {
                  return input[i];
                }
              }
              return null;
            }
          });
});
