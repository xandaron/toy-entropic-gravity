import numpy as np
import matplotlib.pyplot as plt

#230401173903
#230401174029
data = np.loadtxt('./output/230401173903.csv', delimiter=',')

x = data[:,0]
y = data[:,1]
y = -y
fig, ax = plt.subplots(1,2)

logx = np.log(x)
logy = np.log(np.absolute(y))
xseg = np.linspace(min(logx), max(logx), len(logx))
m, c = np.polyfit(logx, logy, 1)
ax[0].scatter(logx, logy, c=logx)
ax[0].plot(xseg, m* xseg + c)
ax[0].set_title(f'ln(|S\'(x)|) = |ln(a) - b ln(x)|\nb = {-m}\na = {c}')
ax[0].set_xlabel('ln(x)')
ax[0].set_ylabel('|ln(S\'(x))|')
print(f'm = {m}\nc = {c}')

ax[1].scatter(x, data[:,2], c=x)
ax[1].set_title("Entropy(S) vs Distance(x)")
ax[1].set_xlabel("Distance(x)")
ax[1].set_ylabel("Entropy(S)")
plt.show()