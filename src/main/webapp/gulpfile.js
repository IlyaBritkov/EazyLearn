const gulp        = require('gulp');
const browserSync = require('browser-sync');
const sass        = require('gulp-sass');
const cleanCSS = require('gulp-clean-css');
const autoprefixer = require('gulp-autoprefixer');
const rename = require("gulp-rename");

gulp.task('server', function() {

    browserSync({
        server: {
            baseDir: "WEB-INF/view/ "
        }
    });


    gulp.watch("WEB-INF/*.html").on('change', browserSync.reload);
});

gulp.task('styles', function() {
    return gulp.src("WEB-INF/resourses/sass/**/*.+(scss|sass)")
        .pipe(sass({outputStyle: 'compressed'}).on('error', sass.logError))
        .pipe(rename({
            suffix: '.min',
            prefix: ''
        }))
        .pipe(autoprefixer())
        .pipe(cleanCSS({compatibility: 'ie8'}))
        .pipe(gulp.dest("WEB-INF/resourses/css"))
        .pipe(browserSync.stream());
});

gulp.task('watch', function() {
    gulp.watch("WEB-INF/resourses/sass/*.+(scss|sass)", gulp.parallel('styles'));
    // gulp.watch("WEB-INF/view/**/*.html").on("change", browserSync.reload);
})

gulp.task('default', gulp.parallel('watch', 'server', 'styles'));

gulp.task('watchStyles', gulp.parallel('watch','styles'));
