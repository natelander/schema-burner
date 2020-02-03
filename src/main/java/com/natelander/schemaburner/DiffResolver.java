package com.natelander.schemaburner;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;

import java.io.File;
import java.util.List;

public class DiffResolver {
    public static void calculateDiff(String repoPath, String commitA, String commitB) throws java.io.IOException {
        Repository repo = new FileRepositoryBuilder().setGitDir(new File(repoPath)).setMustExist(true).build();
        Git git = new Git(repo);

        ObjectId commitAObj = ObjectId.fromString(commitA);
        ObjectId commitBObj = ObjectId.fromString(commitB);

        try(RevWalk revWalk = new RevWalk(repo)) {
            RevCommit revCommitA = revWalk.parseCommit(commitAObj);
            RevCommit revCommitB = revWalk.parseCommit(commitBObj);

            try(DiffFormatter diffFormatter = new DiffFormatter(System.out)) {
                diffFormatter.setRepository(repo);

                List<DiffEntry> diffs = diffFormatter.scan(revCommitA, revCommitB);
                for(DiffEntry entry : diffs) {
                    diffFormatter.format(diffFormatter.toFileHeader(entry));
                }
            }
        }
    }
}
