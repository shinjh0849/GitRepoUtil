package GitRepoUtil;

import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.eclipse.jgit.revwalk.RevCommit;

public class DevTime {
	
	Date lastDate;
	Date firstDate;
	CLIOptions cli;
	
	public DevTime(CLIOptions arguments) throws NoHeadException, GitAPIException, IOException {
		cli = arguments;
		lastDate = getLastDate();
		firstDate = getFirstDate();
	}

	public Date getLastDate() throws NoHeadException, GitAPIException {
		RevCommit latestCommit = cli.git.log().setMaxCount(1).call().iterator().next();
		Date latestDate = latestCommit.getAuthorIdent().getWhen();

		return latestDate;
	}

	public Date getFirstDate() throws NoHeadException, GitAPIException, IOException {
		Iterable<RevCommit> logs = cli.git.log().all().call();

		final Iterator<RevCommit> itr = logs.iterator();
		RevCommit lastElement = itr.next();
		while (itr.hasNext()) {
			lastElement = itr.next();
		}
		Date firstDate = lastElement.getAuthorIdent().getWhen();

		return firstDate;
	}

	public long getDevTime() throws NoHeadException, GitAPIException, IOException {
		long diffInMillies = Math.abs(lastDate.getTime() - firstDate.getTime());
		long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
		
		return diff;
	}
}
