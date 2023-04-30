tasks.register<Copy>("installGitHooks") {
    from(layout.projectDirectory.dir("git-hooks"))
    into(File(rootDir, "/.git/hooks"))
}
