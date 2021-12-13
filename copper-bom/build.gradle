dependencies {
    constraints {
        for (subproject in rootProject.subprojects) {
            if (subproject == project) { // the bom itself
                continue
            }

            api(project(subproject.path))
        }
    }
}
